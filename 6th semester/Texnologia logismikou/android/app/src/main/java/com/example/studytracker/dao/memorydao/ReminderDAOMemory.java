package com.example.studytracker.dao.memorydao;

import com.example.studytracker.dao.daoStub.ReminderDAO;
import com.example.studytracker.domain.Reminder;

import java.util.ArrayList;
import java.util.List;

/**
 * A memory implementation of {@link ReminderDAO}
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class ReminderDAOMemory implements ReminderDAO {
    protected static List<Reminder> reminderList = new ArrayList<>();
    private static int autoincrementId = 1;


    public ReminderDAOMemory() {
    }


    @Override
    public Reminder find(int reminderId) {
        for (Reminder c : reminderList) {
            if (c.getId() == reminderId) return c;
        }
        return null;
    }

    @Override
    public void save(Reminder reminder) {
        delete(reminder.getId());
        reminderList.add(reminder);
        reminder.setId(nextId());
    }

    @Override
    public void delete(int reminderId) {
        for (Reminder c : reminderList) {
            if (c.getId() == reminderId) {
                reminderList.remove(c);
                break;
            }
        }
    }

    @Override
    public List<Reminder> findAll() {
        return reminderList;
    }

    @Override
    public int nextId() {
        autoincrementId++;
        return autoincrementId;
    }
}
