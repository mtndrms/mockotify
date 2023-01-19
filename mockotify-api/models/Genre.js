const mongoose = require("mongoose");
const { Schema } = mongoose;

const Genre = new Schema({
  genre: String,
});

module.exports = mongoose.model("Genre", Genre);
