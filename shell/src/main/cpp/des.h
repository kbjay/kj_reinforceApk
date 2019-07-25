
#ifndef APICRYPT_DES_H
#define APICRYPT_DES_H


unsigned char *des_decipher(unsigned char *ciphertext, int len);

unsigned char *des_encipher(unsigned char *plaintext, int len);

void des_encryptFile(char *sourceFile, char *destFile);

void des_decryptFile(char *sourceFile, char *destFile);

#endif //APICRYPT_DES_H

