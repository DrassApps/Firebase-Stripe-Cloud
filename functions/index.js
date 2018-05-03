/**
Cloud Firebase functions as endPoints to handle database and stripe account.
**/

// Use express to simplify HTTP verbs and routes
const express = require('express');
const admin = require('firebase-admin');
const cors = require('cors')({origin: true});
const functions = require('firebase-functions');

// TODO: [DEVELOPER] - Add stripe secret key 
const stripe = require('stripe')('');

// Initialize express
const app = express();

admin.initializeApp(functions.config().firebase);
app.use(cors);
app.disable("x-powered-by");

// Only allows get method and retieve user card data
app.get("/card/:uid", function getCard(req, res) {
    var cardObject = {};
    var cardKey = 'Card1';
    cardObject[cardKey] = [];

    admin.database().ref().child(`stripe_customers/${req.params.uid}/sources`).once('value').then(snapshot => {
        cardObject[cardKey].push(snapshot.val());
        res.status(200).send(cardObject);  
    });
});

// Only allos method post and add a customer to Strope account
app.post("/customer/:email", function setCustomer(req, res) {
	return stripe.customers.create({ 
		email: req.params.email,
  	}).then((customer) => {
    	res.status(200).json({ message: "Success, new customer added!" });
  	}).catch(err => {
  		res.status(500).json({ error: err });
    });
});

// Export api allow developer make routes like: "https://xxx.firebaseproject/api/.."
exports.api = functions.https.onRequest(app);