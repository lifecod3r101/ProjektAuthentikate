package com.authtoken.Controllers;

import com.authtoken.Models.AuthentikateRoles;
import com.authtoken.Models.AuthentikateUsers;
import com.authtoken.Repositories.AuthentikateRoleRepository;
import com.authtoken.Repositories.AuthentikateUserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class AuthentikateRoleController {
    @Autowired
    AuthentikateRoleRepository authentikateRoleRepository;

    @Autowired
    AuthentikateUserRepository authentikateUserRepository;

    @GetMapping("/getAll")
    public ResponseEntity<List<AuthentikateRoles>> getAllRoles() {
        List<AuthentikateRoles> authentikateRolesList = (List<AuthentikateRoles>) authentikateRoleRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(authentikateRolesList);
    }

    @PostMapping("/create")
    public ResponseEntity<AuthentikateRoles> createRole(@RequestParam("roleName") String roleName) {
        AuthentikateRoles authentikateRole = new AuthentikateRoles();
        authentikateRole.setRoleName(roleName);
        authentikateRoleRepository.save(authentikateRole);
        return ResponseEntity.status(HttpStatus.OK).body(authentikateRole);
    }

    @PostMapping("/assign")
    public ResponseEntity<AuthentikateRoles> assignRoleToUser(@RequestParam("roleId") String roleId, @RequestParam("userId") String userId) {
        AuthentikateRoles authentikateRole = authentikateRoleRepository.findById(roleId).get();
        AuthentikateUsers authentikateUser = authentikateUserRepository.findById(userId).get();
        authentikateRole.getAssociatedRoleUsers().add(authentikateUser);
        authentikateRoleRepository.save(authentikateRole);
        return ResponseEntity.status(HttpStatus.OK).body(authentikateRole);
    }

    @DeleteMapping("/removeFromRole")
    public ResponseEntity<AuthentikateRoles> removeUserFromRole(@RequestParam("roleId") String roleId, @RequestParam("userId") String userId) {
        AuthentikateRoles authentikateRole = authentikateRoleRepository.findById(roleId).get();
        AuthentikateUsers authentikateUser = authentikateUserRepository.findById(userId).get();
        authentikateRole.getAssociatedRoleUsers().remove(authentikateUser);
        authentikateRoleRepository.save(authentikateRole);
        return ResponseEntity.status(HttpStatus.OK).body(authentikateRole);
    }

    @DeleteMapping("/removeRole")
    public ResponseEntity<String> removeUserRole(@RequestParam("roleId") String roleId) {
        authentikateRoleRepository.deleteById(roleId);
        return ResponseEntity.status(HttpStatus.OK).body("Role Removed");
    }

    @GetMapping("/downloadRoleData")
    public ResponseEntity<?> downloadRoleData(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=RoleList" + currentDateTime + ".csv";
        httpServletResponse.setHeader(headerKey, headerValue);
        List<AuthentikateRoles> authentikateRolesList = (List<AuthentikateRoles>) authentikateRoleRepository.findAll();
        ICsvBeanWriter listWriter = new CsvBeanWriter(httpServletResponse.getWriter(), CsvPreference.EXCEL_PREFERENCE);
        String[] rolesHeader = {"roleId", "roleName"};
        listWriter.writeHeader(rolesHeader);
        for (AuthentikateRoles authentikateRole : authentikateRolesList) {
            listWriter.write(authentikateRole, rolesHeader);
        }
        listWriter.close();
        return ResponseEntity.status(HttpStatus.OK).body(authentikateRolesList);
    }
}
