const express = require('express')
const Job = require('../models/job')
const router = new express.Router()

router.post('/jobs', async (req, res) => {
     const job = new Job(req.body)

    try {
        await job.save()
        res.send(job)
    } catch (e) {
        res.status(404).send()
    }
})

module.exports = router

