const express = require ('express')
require('./db/mongoose')
const jobsRouter = require('./routers/jobs')

const app = express()
app.use(express.json())
app.use(jobsRouter)

const port = process.env.port || 3000

app.listen(port, () => {
    console.log('App is listening on port ' + port)
})