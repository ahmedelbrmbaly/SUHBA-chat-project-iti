package com.suhba.services.controllers;

import com.suhba.database.entities.User;
import com.suhba.network.ServerClientServices;
import com.suhba.network.ServerService;
import javafx.scene.control.Alert;

import java.rmi.RemoteException;
import java.util.List;

public class ClientAddContactScreenService {
    ServerClientServices serverService = ServerService.getInstance();

    private User getCurUser () {
        if (SignIn1Service.curUser != null) {
            System.out.println("If from login: The cur user id = " + SignIn1Service.curUser.getUserId());
            return SignIn1Service.curUser;
        }
        else if (SignUp2Service.curRegisterdUser != null) {
            System.out.println("If from signup: The cur user id = " + SignUp2Service.curRegisterdUser.getUserId());
            return SignUp2Service.curRegisterdUser;
        }
        return null;
    }

    public boolean sendFriendRequest (List<String> phoneNumbers) throws RemoteException {
        List<Long> receiverIds = serverService.getUserIdsByPhones(phoneNumbers);
        /// //////////////////////////////////////////////////////////////////////////////
        if (receiverIds != null) return serverService.sendFriendRequestsById(getCurUser().getUserId(), receiverIds);
        return true;
    }

    public String getDisplayNameByPhoneNumber (String phone) throws RemoteException {
        User user = serverService.getUserByPhoneNumber(phone);
        if (user != null)  return user.getDisplayName();
        return null;
    }

    public boolean isMe (String phoneNumber) throws RemoteException {
        User user = serverService.getUserByPhoneNumber(phoneNumber);
        if (user != null)  return user.getUserId() == getCurUser().getUserId();
        return false;
    }

    public void showErrorAlert (String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
