const mongoose = require('mongoose')
const date = require('date-time')

const jobsSchema = mongoose.Schema({
    latitude: {
        type: String,
        required: true
    },
    longitude: {
        type: String,
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
    beginDate: {
        type: Date,
        required: true,
        default: date()
    },
    endDate: {
        type: Date,
        required: true,
        default: date()
    }
})

const Jobs = mongoose.model('Jobs', jobsSchema)
module.exports = Jobs