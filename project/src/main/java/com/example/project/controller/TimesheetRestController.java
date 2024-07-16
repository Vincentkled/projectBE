package com.example.project.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.handler.CustomResponse;
import com.example.project.model.Timesheet;
import com.example.project.repository.TimesheetRepository;

@RestController
@CrossOrigin
@RequestMapping("api")
public class TimesheetRestController {

    @Autowired
    private TimesheetRepository timesheetRepository;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("timesheet")
    public ResponseEntity<Object> get() {
        List<Timesheet> data = timesheetRepository.findAll();
        if (data.isEmpty()) {
            return CustomResponse.generate(HttpStatus.OK, "Data tidak ditemukan", data);
        }
        return CustomResponse.generate(HttpStatus.OK, "Data ditemukan", data);
    }

    @PostMapping(value = {"timesheet", "timesheet/{id}"})
    public ResponseEntity<Object> save(@RequestBody Timesheet timesheet, @PathVariable(required = false) Integer id) throws AddressException, MessagingException {
        if (id != null) {
            boolean timesheetExists = timesheetRepository.existsById(id);
            if (timesheetExists) {
                timesheet.setId(id);
                timesheetRepository.save(timesheet);

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                String formattedDateTime = now.format(dtf);
                MimeMessage message = mailSender.createMimeMessage();

                message.setFrom(new InternetAddress("vincentkled27@gmail.com"));
                String email = timesheetRepository.findEmail(id);
                message.setRecipients(MimeMessage.RecipientType.TO, email);
                message.setSubject("Timesheet has been Updated" + "[" + formattedDateTime + "]");

                String htmlContent = "<div style=\"text-align: center;\">" +
                    "<h1 style=\"color: black; font-size: 20px;\">Good day to you, Mr/Ms. " + timesheet.getEmployee().getName() + "</h1>" +
                    "<hr>" +
                    "<p style=\"color: black; font-size: 14px;\"><p>Your Timesheet on Date: <b>" + timesheet.getDateentity().getDatetb() +
                    "</b>, indicates that your attendance is: <b>" + timesheet.getAttendance() + "</b> and the Activity: <b>" + timesheet.getActivity() +
                    "</b> has been</p></p>" +
                    "<h1 style=\"color: green; font-size: 18px;\"><b>Updated!</b></h1>" +
                    "<p style=\"color: black; font-size: 12px;\">Please check your <b>Timesheet</b></p>" +
                    "<p style=\"color: black; font-size: 12px;\">Thank you for using our Timesheet.<b>-Admin</b></p>" +
                    "<hr>" +
                    "</div>";

                message.setContent(htmlContent, "text/html; charset=utf-8");
                mailSender.send(message);
                System.out.println("Message sent Successfully");
                return CustomResponse.generate(HttpStatus.OK, "Data Berhasil di Edit");
            }
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Timesheet dengan ID " + id + " tidak ditemukan");
        } else {
            timesheetRepository.save(timesheet);
            return CustomResponse.generate(HttpStatus.OK, "Data Berhasil di Simpan");
        }
    }

    @DeleteMapping("timesheet/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Integer id) {
        Boolean result = timesheetRepository.findById(id).isPresent();
        if (result) {
            timesheetRepository.deleteById(id);
            return CustomResponse.generate(HttpStatus.OK, "Berhasil Menghapus Data");
        }
        return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Data yang ingin diHapus tidak ditemukan");
    }

    @GetMapping("timesheet/{id}")
    public ResponseEntity<Object> get(@PathVariable(required = true) Integer id) {
        Boolean result = timesheetRepository.findById(id).isPresent();
        if (result) {
            Timesheet timesheet = timesheetRepository.findById(id).orElse(null);
            return CustomResponse.generate(HttpStatus.OK, "Data ditemukan", timesheet);
        }
        return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Data Tidak ditemukan", result);
    }
}
