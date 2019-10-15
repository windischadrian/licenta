const express = require ('express')
const app = express()

const port = process.env.port || 3000

app.post('/', (req, res) => {
    res.send('Primul nostru post')
})

app.get('/', (req, res) => {
    res.send('Primul nostru post')
})

app.listen(port, () => {
    console.log('App is listening on port ' + port)
})