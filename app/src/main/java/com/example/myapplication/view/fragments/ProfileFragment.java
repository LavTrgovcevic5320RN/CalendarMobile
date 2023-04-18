package com.example.myapplication.view.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import timber.log.Timber;

public class ProfileFragment extends Fragment {

    private ImageView profilePicture;
    private TextView email;
    private Button changePasswordBtn;
    private Button logOutBtn;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profilePicture = view.findViewById(R.id.profile_picture);
        email = view.findViewById(R.id.email);
        changePasswordBtn = view.findViewById(R.id.change_password_btn);
        logOutBtn = view.findViewById(R.id.log_out_btn);

        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String emailInfo = sharedPreferences.getString("email", "");

        profilePicture.setImageResource(R.drawable.default_profile_picture);
        email.setText(emailInfo);

        changePasswordBtn.setOnClickListener(v -> showPasswordChangeDialog());
        logOutBtn.setOnClickListener(v -> showLogOutDialog());

        return view;
    }

    private void showPasswordChangeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Change Password");
        builder.setMessage("Please enter your old and new password.");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_change_password, null);
        builder.setView(dialogView);

        builder.setPositiveButton("Change", (dialog, which) -> {
            EditText OldPassword = dialogView.findViewById(R.id.old_password);
            EditText NewPassword = dialogView.findViewById(R.id.new_password);
            EditText ConfirmNewPassword = dialogView.findViewById(R.id.confirm_password);

            String oldPassword = OldPassword.getText().toString();
            String newPassword = NewPassword.getText().toString();
            String confirmNewPassword = ConfirmNewPassword.getText().toString();

            String emailInfo = sharedPreferences.getString("email", "");
            String passwordInfo = sharedPreferences.getString("password", "");

            if(!oldPassword.equals(passwordInfo)){
                Toast.makeText(getActivity(), "The old password is wrong", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!newPassword.equals(confirmNewPassword)){
                Toast.makeText(getActivity(), "The new password isn't the same", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("password", newPassword);
            editor.apply();

            changePassword(emailInfo, oldPassword, newPassword);

            Toast.makeText(getActivity(), "Password change successful.", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void changePassword(String email, String oldPassword, String newPassword) {
        try {
            File dir = new File(getContext().getFilesDir(), "mydir");
            File gpxfile = new File(dir, "password.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(gpxfile));

            File tempFile = new File(gpxfile.getAbsolutePath() + "1.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean found = false;

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("=");
                String currEmail = parts[0];
                String currPassword = parts[1];

                if (currEmail.equals(email)) {
                    if (currPassword.equals(oldPassword)) {
                        currPassword = newPassword;
                        found = true;
                    } else {
                        Timber.d("Incorrect password");
                    }
                }

                writer.write(currEmail + "=" + currPassword);
                writer.newLine();
            }

            bufferedReader.close();
            writer.close();

            if (found) {
                gpxfile.delete();
                tempFile.renameTo(gpxfile);

            } else {
                tempFile.delete();
                Timber.d("Email not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showLogOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Log Out");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Log Out", (dialog, which) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("is_logged_in", false);
            editor.apply();

            Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();

            requireActivity().finish();
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

}

