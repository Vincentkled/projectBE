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
import org.springframework.web.bind.annotation.*;

import com.example.project.handler.CustomResponse;
import com.example.project.model.Timesheet;
import com.example.project.repository.TimesheetRepository;
@RestController
@CrossOrigin
@RequestMapping("api")
public class TimesheetRestController {

    private static final String REJECTED_STATUS = "Rejected";
    private static final String APPROVED_STATUS = "Approved";

    @Autowired
    private TimesheetRepository timesheetRepository;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("timesheet")
    public ResponseEntity<Object> getAllTimesheets() {
        List<Timesheet> data = timesheetRepository.findAll();
        if (data.isEmpty()) {
            return CustomResponse.generate(HttpStatus.OK, "Data tidak ditemukan", data);
        }
        return CustomResponse.generate(HttpStatus.OK, "Data ditemukan", data);
    }

    @PostMapping(value = {"timesheet", "timesheet/{id}"})
    public ResponseEntity<Object> saveTimesheet(@RequestBody Timesheet timesheet, @PathVariable(required = false) Integer id, @RequestParam(required = false) String note) throws AddressException, MessagingException {
        if (id != null) {
            return updateTimesheet(timesheet, id, note);
        } else {
            timesheetRepository.save(timesheet);
            return CustomResponse.generate(HttpStatus.OK, "Data Berhasil di Simpan");
        }
    }

    private ResponseEntity<Object> updateTimesheet(Timesheet timesheet, Integer id, String note) throws AddressException, MessagingException {
        boolean timesheetExists = timesheetRepository.existsById(id);
        if (!timesheetExists) {
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Timesheet dengan ID " + id + " tidak ditemukan");
        }
        
        // Retrieve and update the existing Timesheet
        Timesheet existingTimesheet = timesheetRepository.findById(id).orElseThrow(() -> new RuntimeException("Timesheet not found"));
        existingTimesheet.setStart_time(timesheet.getStart_time());
        existingTimesheet.setEnd_time(timesheet.getEnd_time());
        existingTimesheet.setActivity(timesheet.getActivity());
        existingTimesheet.setAttendance(timesheet.getAttendance());
        existingTimesheet.setStatus(timesheet.getStatus());
        existingTimesheet.setEmployee(timesheet.getEmployee());
        existingTimesheet.setDateentity(timesheet.getDateentity());

        timesheetRepository.save(existingTimesheet);

        if (REJECTED_STATUS.equals(existingTimesheet.getStatus())) {
            sendEmail(existingTimesheet, note, "Rejected", "red");
        } else if (APPROVED_STATUS.equals(existingTimesheet.getStatus())) {
            sendEmail(existingTimesheet, note, "Approved", "green");
        }

        return CustomResponse.generate(HttpStatus.OK, "Data Berhasil di Edit");
    }

    private void sendEmail(Timesheet timesheet, String note, String status, String color) throws AddressException, MessagingException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = now.format(dtf);
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("vincentkled27@gmail.com"));
        String email = timesheetRepository.findEmail(timesheet.getId());
        message.setRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("Timesheet has been " + status + " [" + formattedDateTime + "]");

        String htmlContent = "<div style=\"text-align: center;\">" +
                "<h1 style=\"color: black; font-size: 20px;\">Good day to you, Mr/Ms. " + timesheet.getEmployee().getName() + "</h1>" +
                "<hr>" +
                "<p style=\"color: black; font-size: 14px;\">Your Timesheet on Date: <b>" + timesheet.getDateentity().getDatetb() +
                "</b>, indicates that your attendance is: <b>" + timesheet.getAttendance() + "</b> and the Activity: <b>" + timesheet.getActivity() +
                "</b> has been</p>" +
                "<h1 style=\"color: " + color + "; font-size: 30px;\"><b>" + status + "!</b></h1>" +
                "<p style=\"color: black; font-size: 12px;\">Note: <b>" + note + "</b></p>" +
                "<p style=\"color: black; font-size: 12px;\">Please check your <b>Timesheet</b></p>" +
                "<p style=\"color: black; font-size: 12px;\">Thank you for using our Timesheet.<b>-Admin</b></p>" +
                "<hr>" +
                "</div>";

        message.setContent(htmlContent, "text/html; charset=utf-8");
        mailSender.send(message);
        System.out.println("Message sent Successfully");
    }

    @DeleteMapping("timesheet/{id}")
    public ResponseEntity<Object> deleteTimesheet(@PathVariable Integer id) {
        if (timesheetRepository.existsById(id)) {
            timesheetRepository.deleteById(id);
            return CustomResponse.generate(HttpStatus.OK, "Berhasil Menghapus Data");
        }
        return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Data yang ingin diHapus tidak ditemukan");
    }

    @GetMapping("timesheet/{id}")
    public ResponseEntity<Object> getTimesheet(@PathVariable Integer id) {
        return timesheetRepository.findById(id)
                .map(timesheet -> CustomResponse.generate(HttpStatus.OK, "Data ditemukan", timesheet))
                .orElseGet(() -> CustomResponse.generate(HttpStatus.BAD_REQUEST, "Data Tidak ditemukan", false));
    }
}