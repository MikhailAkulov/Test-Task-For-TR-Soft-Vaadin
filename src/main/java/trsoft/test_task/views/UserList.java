package trsoft.test_task.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import trsoft.test_task.model.User;
import trsoft.test_task.repository.UserRepository;

import java.util.List;

@Route("users")
public class UserList extends AppLayout {
    VerticalLayout layout;
    Grid<User> grid;
    RouterLink linkCreate;

    UserRepository userRepository;

    @Autowired
    public UserList(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserList() {
        layout = new VerticalLayout();
        grid = new Grid<>();
        linkCreate = new RouterLink("Добавить пользователя", ManageUser.class, 0);
        layout.add(linkCreate);
        layout.add(grid);
        addToNavbar(new H3("Список пользователей"));
        setContent(layout);
    }


    @PostConstruct
    public void fillGrid() {
        List<User> users = userRepository.findAll();
        if (!users.isEmpty()) {

            // расстановка столбцов в нужном порядке
            grid.addColumn(User::getLastName).setHeader("Фамилия");
            grid.addColumn(User::getFirstName).setHeader("Имя");
            grid.addColumn(User::getMiddleName).setHeader("Отчество");
            grid.addColumn(User::getBirthdayDate).setHeader("Д. Р.");
            grid.addColumn(User::getEmail).setHeader("E-mail");
            grid.addColumn(User::getPhoneNumber).setHeader("Номер");

            // кнопки удаления и редактирования
            grid.addColumn(new NativeButtonRenderer<>("Редактировать", user -> {
                UI.getCurrent().navigate(ManageUser.class, user.getId());
            }));
            grid.addColumn(new NativeButtonRenderer<>("Удалить", user -> {
                Dialog dialog = new Dialog();
                Button confirm = new Button("Удалить");
                Button cancel = new Button("Отмена");
                dialog.add("Точно удалить контакт?");
                dialog.add(confirm);
                dialog.add(cancel);

                confirm.addClickListener(clickEvent -> {
                    userRepository.delete(user);
                    dialog.close();
                    Notification notification = new Notification("Пользователь удалён", 1000);
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.open();
                    grid.setItems(userRepository.findAll());
                });
                cancel.addClickListener(clickEvent -> {
                    dialog.close();
                });
                dialog.open();
            }));
            grid.setItems(users);
        }
    }
}
