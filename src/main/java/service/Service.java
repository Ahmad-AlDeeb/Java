package service;

@Service
public class Service {
    Component component;

    public Service(Component component) {
        this.component = component;
    }

    public String doTask(String prompt) {
        return component.doTask(prompt);
    }
}
