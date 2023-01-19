const mongoose = require("mongoose");
const { Schema } = mongoose;

const Track = new Schema({
  albumType: String,
  artists: [{ href: String, name: String }],
});

module.exports = mongoose.model("Track", Track);
