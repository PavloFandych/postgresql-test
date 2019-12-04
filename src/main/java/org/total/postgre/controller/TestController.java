package org.total.postgre.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.total.postgre.entity.Domain;
import org.total.postgre.entity.Employee;
import org.total.postgre.exception.ResourceNotFoundException;
import org.total.postgre.repository.CapabilityRepository;
import org.total.postgre.repository.DomainRepository;
import org.total.postgre.repository.EmployeeRepository;
import org.total.postgre.repository.RoleRepository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Pavlo.Fandych
 */

@RestController
@RequestMapping("/api/v1")
@Getter
@Slf4j
public class TestController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CapabilityRepository capabilityRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DomainRepository domainRepository;

    @GetMapping("/employees")
    public List<Employee> fetchAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> fetchEmployeeById(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {
        final Employee employee = getEmployeeRepository().findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

        return ResponseEntity.ok().body(employee);
    }

    @GetMapping("/save")
    public ResponseEntity<String> save() {
        try {
            saveHierarchy("/home/total/Desktop");
        } catch (Exception e) {
            final String message = e.getMessage();
            log.error("Exception occurred {}", message);

            return ResponseEntity.badRequest().body(message);
        }

        return ResponseEntity.ok().body("OK");
    }

    private void saveHierarchy(final String path) {
        final Map<String, Domain> allDomains = new HashMap<>();
        final Map<String, List<File>> domainNameChildrenMap = new HashMap<>();
        final Map<String, String> domainNameParentDomainNameMap = new HashMap<>();

        prepareMaps(path, allDomains, domainNameChildrenMap, domainNameParentDomainNameMap);

        allDomains.forEach((key, value) -> {
            final String parentPath = domainNameParentDomainNameMap.get(key);
            final Domain parent = allDomains.get(parentPath);

            value.setParent(parent);

            final List<File> children = domainNameChildrenMap.get(key);
            if (children != null) {
                final List<Domain> childrenDomains = new ArrayList<>();
                for (File file : children) {
                    final String name = file.getName();
                    final Domain domain = allDomains.get(name);
                    childrenDomains.add(domain);
                }

                value.setChildren(childrenDomains);
            } else {
                value.setChildren(null);
            }
        });

        populate(allDomains);
    }

    private void populate(final Map<String, Domain> allDomains) {
        allDomains.forEach((key, value) -> {
            final Domain byDomainName = getDomainRepository().findByDomainName(key);
            if (byDomainName != null) {
                final List<Domain> fromDb = byDomainName.getChildren();
                final List<Domain> valueChildren = value.getChildren();

                if (valueChildren != null) {
                    valueChildren.stream().filter(domain -> !fromDb.contains(domain)).forEach(fromDb::add);
                }

                value.setChildren(fromDb);

                final Domain parentFromDb = byDomainName.getParent();
                final Domain parentFromValue = value.getParent();
                if (!parentFromValue.equals(parentFromDb)) {
                    value.setParent(parentFromValue);
                }
            }

            getDomainRepository().save(value);
        });
    }

    private void prepareMaps(final String stringPath, final Map<String, Domain> domainNameDomainMap,
            final Map<String, List<File>> domainNameChildrenMap, final Map<String, String> domainNameParentDomainNameMap) {
        try (Stream<Path> paths = Files.walk(Paths.get(stringPath))) {
            paths.forEach(item -> {
                final String domainName = item.getFileName().toString();
                final Path parent = item.getParent();
                if (parent != null) {
                    domainNameParentDomainNameMap.put(domainName, parent.getFileName().toString());
                } else {
                    domainNameParentDomainNameMap.put(domainName, null);
                }

                List<File> children = null;
                String[] directDirectories;
                if (item.toFile().isDirectory()) {
                    children = (List<File>) FileUtils.listFiles(item.toFile(), null, false);
                    directDirectories = item.toFile().list((current, name) -> new File(current, name).isDirectory());
                    if (directDirectories != null) {
                        for (String name : directDirectories) {
                            children.add(new File(name));
                        }
                    }
                }
                final Domain domain = new Domain();
                domain.setDomainName(domainName);

                domainNameDomainMap.put(domainName, domain);
                if (children != null) {
                    domainNameChildrenMap.put(domainName, children);
                }
            });
        } catch (Exception e) {
            log.error("Exception occurred.", e);
        }
    }
}