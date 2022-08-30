 'use strict';

 const FabricCAServices = require('fabric-ca-client');
 const { Wallets, X509WalletMixin } = require('fabric-network');
 const fs = require('fs');
 const path = require('path');

 const ccpPath = path.resolve(__dirname, 'connection.json');
 const ccpJSON = fs.readFileSync(ccpPath, 'utf8');
 const ccp = JSON.parse(ccpJSON);

 async function main() {
   try {

   // Create a new CA client for interacting with the CA.
   const caURL = "http://org1ca-api.127-0-0-1.nip.io:8080";
   const ca = new FabricCAServices(caURL);

   // Create a new file system based wallet for managing identities.
   const walletPath = path.join(process.cwd(), 'wallet');
   const wallet = await Wallets.newFileSystemWallet(walletPath);
   console.log(`Wallet path: ${walletPath}`);


   // Enroll the admin user, and import the new identity into the wallet.
   const enrollment = await ca.enroll({ enrollmentID: 'admin', enrollmentSecret: 'adminpw' });
  //  const identity = X509WalletMixin.createIdentity('novousuario', enrollment.certificate, enrollment.key.toBytes());
  //  await wallet.import('novousuario', identity);
  //  console.log('Successfully enrolled client "novousuario" and imported it into the wallet');

           // Enroll the admin user, and import the new identity into the wallet.
           //const enrollment = await ca.enroll({ enrollmentID: 'novousuario', enrollmentSecret: 'novousuariopw' });
           const x509Identity = {
               credentials: {
                   certificate: enrollment.certificate,
                   privateKey: enrollment.key.toBytes(),
               },
               mspId: 'Org1MSP',
               type: 'X.509',
           };
           await wallet.put('admin', x509Identity);
           console.log('Successfully enrolled admin user "admin" and imported it into the wallet');


   } catch (error) {
     console.error(`Failed to enroll "admin": ${error}`);
     process.exit(1);
   }
 }

 main();
