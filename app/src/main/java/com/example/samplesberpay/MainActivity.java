package com.example.samplesberpay;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.security.SecureRandom;

import sberid.sdk.auth.login.SberIDLoginManager;
import sberid.sdk.auth.pkce.PkceUtils;
import sberid.sdk.auth.view.SberIDButton;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SberIDButton sberIDButton = findViewById(R.id.sber_button);

        sberIDButton.setOnClickListener(v -> {
            SberIDLoginManager sberIDLoginManager = new SberIDLoginManager();

//Создание параметров для поддержки протокола PKCE.
            String codeVerifier = PkceUtils.Companion.generateRandomCodeVerifier(new SecureRandom());
            String codeChallenge = PkceUtils.Companion.deriveCodeVerifierChallenge(codeVerifier);


//Создание Uri с параметрами для аутентификации, все значения нужно поменять на свои, тут указаны примеры
            Uri uri = SberIDLoginManager.Companion
                    .sberIDBuilder()
                    .clientID("512e1152-e14c-5223-a125-d519eb68bb86")
                    .scope("openid name email mobile birthdate gender")
                    .state("ffad1d59c1e34844a1415226103d44f3")
                    .nonce("b1947d4f10a24eb0a21155239be9b066")
                    .redirectUri("app://merchant_app/")
                    .codeChallenge(codeChallenge) //Необязательный параметр
                    .codeChallengeMethod(PkceUtils.Companion.getCodeChallengeMethod()) //Необязательный параметр
                    .build();


//Запуск аутентификации по SberbankID, первым параметром нужно передать контекст
            sberIDLoginManager.loginWithSberbankID(this, uri);
        });

    }
}
