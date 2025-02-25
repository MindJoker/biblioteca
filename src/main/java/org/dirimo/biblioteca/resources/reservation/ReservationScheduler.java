package org.dirimo.biblioteca.resources.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ReservationScheduler {
//solo job

    private final ReservationService reservationService;

    @Scheduled(cron = "0 41 9 * * ?")
    public void sendReminder() {
        reservationService.sendReminderEmails();
        }
    }