const mongoose = require('mongoose')

mongoose.connect('mongodb+srv://shucker96:pungatomic2@firstcluster-xzapv.mongodb.net/fast-jobs?retryWrites=true&w=majority', {
    type: 'mongodb',
    useNewUrlParser: true,
    useCreateIndex: true,
    useUnifiedTopology: true,
    useFindAndModify: false
})

