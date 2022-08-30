// content of index.js
const http = require('http')
const express = require("express");
const bodyParser = require("body-parser");
const fileUpload = require('express-fileupload');
const cors = require('cors');
const morgan = require('morgan');
const _ = require('lodash');
const path = require('path');

const port = 4444

// async function main() {
//   contract.create("0008", "00008")
// }
main();


async function main() {

app = express();

app.use(express.json())
app.use(fileUpload({
    createParentPath: true
}));
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));
app.use(morgan('dev'))
app.use(express.static('static'));
app.use('/', express.static("static"))

app.listen(port, function(err) {
   console.log("Servidor rodando na porta" + port);
})

}
