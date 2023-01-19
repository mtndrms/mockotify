const mongoose = require("mongoose");
const express = require("express");
const bodyParser = require("body-parser");
require("dotenv").config();

mongoose.set("strictQuery", false);
const app = express();
app.use(bodyParser.json());

// IMPORT ROUTES
const tracksRoute = require("./routes/tracks");
const genresRoute = require("./routes/genres");

// ROUTES
app.use("/tracks", tracksRoute);
app.use("/genres", genresRoute);

app.get("/", (req, res) => {
  res.send("Hello World!");
});

let DB_USERNAME = process.env.DB_USERNAME;
let DB_PASSWORD = process.env.DB_PASSWORD;
let DB_NAME = process.env.DB_NAME;

mongoose.connect(
  "mongodb+srv://" +
    DB_USERNAME +
    ":" +
    DB_PASSWORD +
    "@mockotify.lgxkbut.mongodb.net/" +
    DB_NAME +
    "?retryWrites=true&w=majority",
  () => {
    console.log("Successfully connected!");
  }
);

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Listening on port ${PORT}`);
});
