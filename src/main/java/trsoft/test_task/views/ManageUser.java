package trsoft.test_task.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import trsoft.test_task.model.User;
import trsoft.test_task.repository.UserRepository;

import java.util.Optional;

@Route("manageUser")
public class ManageUser extends AppLayout implements HasUrlParameter<Integer> {

    Integer id;
    FormLayout userForm;
    TextField firstName;
    TextField middleName;
    TextField lastName;
    TextField birthdayDate;
    TextField phoneNumber;
    TextField email;
    Button saveUser;

    @Autowired
    UserRepository userRepository;


    public ManageUser() {
        // создание объектов для формы
        userForm = new FormLayout();
        firstName = new TextField("Имя");
        middleName = new TextField("Отчество");
        lastName = new TextField("Фамилия");
        birthdayDate = new TextField("Дата рождения");
        phoneNumber = new TextField("Номер телефона");
        email = new TextField("Электронная почта");
        saveUser = new Button("Сохранить");

        // добавление элементов в форму
        userForm.add(firstName, middleName, lastName, birthdayDate, phoneNumber, email, saveUser);
        setContent(userForm);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer userId) {
        id = userId;
        if (!id.equals(0)) {
            addToNavbar(new H3("Редактирование данных пользователя"));
        } else {
            addToNavbar(new H3("Добавить пользователя"));
        }
        fillForm();
    }

    public void fillForm() {
        if (!id.equals(0)) {
            Optional<User> user = userRepository.findById(id);
            user.ifPresent(it -> {
                firstName.setValue(it.getFirstName());
                middleName.setValue(it.getMiddleName());
                lastName.setValue(it.getLastName());
                birthdayDate.setValue(it.getBirthdayDate());
                email.setValue(it.getEmail());
                phoneNumber.setValue(it.getPhoneNumber());
            });
        }

        saveUser.addClickListener(clickEvent -> {
            // создание объекта пользователя по значениям, полученным из формы
            User user = new User();
            if (!id.equals(0)) {
                user.setId(id);
            }
            user.setLastName(lastName.getValue());
            user.setFirstName(firstName.getValue());
            user.setMiddleName(middleName.getValue());
            user.setBirthdayDate(birthdayDate.getValue());
            user.setEmail(email.getValue());
            user.setPhoneNumber(phoneNumber.getValue());
            userRepository.save(user);

            // уведомление и переход к списку контактов
            Notification notification = new Notification(id.equals(0) ? "Пользователь добавлен" : "Пользователь был изменён", 1000);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.addDetachListener(detachEvent -> {
                UI.getCurrent().navigate(UserList.class);
            });
            userForm.setEnabled(false);
            notification.open();
        });
    }
}
