package com.astter.in;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.web3j.crypto.Bip32ECKeyPair;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.MnemonicUtils;


public class MainActivity extends AppCompatActivity {
    EditText seed;
    TextView address;
    Button generate;

    private static final String CLIENT_ID = "9bbdae1f28a4846aab57e377753e6ab647ce07a5471e79cca37eb4245b016d84";
    private static final String CLIENT_SECRET = "a68ab9deb28127954dbb24ea661e7e2da45390bbb1e778c9cb00c5148a4dd862";
    private static final String REDIRECT_URI = "https://astterindia.page.link/u9DC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seed = findViewById(R.id.seed);
        address = findViewById(R.id.address);
        generate = findViewById(R.id.generate);



        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seed_phrase = seed.getText().toString();
                Credentials credentials = generateAddress(seed_phrase);
                address.setText(credentials.getAddress());
            }
        });

    }

    Credentials generateAddress(String seed_phrase){
        int[] derivationPath = {44 | Bip32ECKeyPair.HARDENED_BIT, 60 | Bip32ECKeyPair.HARDENED_BIT, Bip32ECKeyPair.HARDENED_BIT, 0,0};

// Generate a BIP32 master keypair from the mnemonic phrase

        Bip32ECKeyPair masterKeypair = Bip32ECKeyPair.generateKeyPair(MnemonicUtils.generateSeed(seed_phrase, null));

// Derived the key using the derivation path
        Bip32ECKeyPair derivedKeyPair = Bip32ECKeyPair.deriveKeyPair(masterKeypair, derivationPath);

// Load the wallet for the derived key

        return Credentials.create(derivedKeyPair);
    }
}