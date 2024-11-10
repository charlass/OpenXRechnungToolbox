package org.oxt.toolbox.cli;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Describe the root element of a jobfile, a "tasks" node
 */
@XmlRootElement
public class Tasks {
    private String description;
    private Task[] tasks;

    @XmlElement(name = "task")
    public Task[] getTasks() {
        return tasks != null ? tasks : new Task[0];
    }

    public void setTasks(Task[] tasks) {
        this.tasks = tasks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
