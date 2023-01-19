const express = require("express");
const Genre = require("../models/Genre");
const router = express.Router();

router.get("/", async (req, res) => {
  try {
    const genres = await Genre.find();
    res.json(genres);
  } catch (err) {
    res.status(400).json({ message: err });
  }
});

module.exports = router;
