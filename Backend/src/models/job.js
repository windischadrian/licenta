const mongoose = require('mongoose')
const date = require('date-time')

const jobsSchema = mongoose.Schema({
    latitude: {
        type: Number,
        required: true
    },
    longitude: {
        type: Number,
        required: true
    },
    description: {
        type: String,
        default: "No description provided"
    },
    type: {
        type: String,
        default: "Other"
    },
    cost: {
        type: String,
        default: '0'
    },
    begin_date: {
        type: Date,
        required: true,
        default: date()
    },
    end_date: {
        type: Date,
        required: true,
        default: date()
    }
})

const Jobs = mongoose.model('Jobs', jobsSchema)
module.exports = Jobs