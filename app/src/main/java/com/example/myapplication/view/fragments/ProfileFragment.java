package com.example.myapplication.view.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.example.myapplication.view.activities.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class ProfileFragment extends Fragment {

    private ImageView profilePicture;
    private TextView email;
    private Button changePasswordBtn;
    private Button logOutBtn;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        profilePicture = view.findViewById(R.id.profile_picture);
        email = view.findViewById(R.id.email);
        changePasswordBtn = view.findViewById(R.id.change_password_btn);
        logOutBtn = view.findViewById(R.id.log_out_btn);
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Set profile picture and email
        // Replace the following lines with your own code to get the user's profile picture and email
        profilePicture.setImageResource(R.drawable.default_profile_picture);
        String emailInfo = sharedPreferences.getString("email", "");
//        Timber.d("USAO 1");
//        Timber.d(emailInfo);
//        Timber.d("USAO 2");
        email.setText(emailInfo);
//        Timber.d(email.getText().toString());

        // Set click listeners for buttons
        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle password change
                // Replace the following lines with your own code to handle password change
                showPasswordChangeDialog();
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle log out
                // Replace the following lines with your own code to handle log out
                showLogOutDialog();
            }
        });

        return view;
    }

    private void showPasswordChangeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Change Password");
        builder.setMessage("Please enter your old and new password.");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_change_password, null);
        builder.setView(dialogView);

        builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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

                changePassword(getContext(), R.raw.password, emailInfo, oldPassword, newPassword);

                Toast.makeText(getActivity(), "Password change successful.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showLogOutDialog() {
        // Replace the following lines with your own code to show a dialog for log out
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Log Out");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle log out
                Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();
                // Replace the following lines with your own code to start the login activity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public boolean readPasswordFromRawResource(Context context, int resourceId, String email, String password) {
        InputStream inputStream = context.getResources().openRawResource(resourceId);
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        Map<String,String> map = new HashMap<>();

        String[] credentials = null;
        try {
            String line = bufferedReader.readLine();
            while(line != null){
                credentials = line.split("=");

                map.put(credentials[0], credentials[1]);

//                Timber.d("email je: %s", credentials[0]);
//                Timber.d("sifra je: %s", credentials[1]);
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
            reader.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }



    public void changePassword(Context context, int resourceId, String email, String oldPassword, String newPassword) {
        try {
            File dir = new File(getContext().getFilesDir(), "mydir");
            File gpxfile = new File(dir, "password.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(gpxfile));

            File tempFile = new File(gpxfile.getAbsolutePath() + "1.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

//            Timber.d("---------------------------------");
//            Timber.d(tempFile.getAbsolutePath());
//            Timber.d("---------------------------------");

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

}

