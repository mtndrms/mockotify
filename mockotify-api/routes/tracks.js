const express = require("express");
const Track = require("../models/Track");
const router = express.Router();

router.get("/", async (req, res) => {
  const { page = 1, limit = 10 } = req.query;
  try {
    const tracks = await Track.find()
      .limit(limit * 1)
      .skip((page - 1) * limit)
      .exec();

    const count = await Track.countDocuments();
    res.json({
      tracks,
      totalPages: Math.ceil(count / limit),
      currentPage: page,
    });
  } catch (err) {
    res.status(400).json({ message: err });
  }
});

router.get("/genre", async (req, res) => {
  try {
    console.log(req.query.genre);
    const tracks = await Track.find({
      genre: req.query.genre,
    });
    res.json(tracks);
  } catch (err) {
    res.status(400).json({ message: err });
  }
});

router.get("/:id", async (req, res) => {
  try {
    const track = await Track.find({
      id: req.params.id,
    });
    res.send(track);
  } catch (err) {
    res.status(400).json({ message: err });
  }
});

module.exports = router;
