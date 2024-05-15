import axios from "axios";
let sum = 0;
let step = 0;
let timePassed = false;
let end_at_time = new Date();
let id=0;

self.onmessage = (event) => {
    const { initialValue, end_at, savings_id } = event.data;
    step = initialValue;
    id=savings_id;
    end_at_time=end_at;
};

const intervalId = setInterval(() => {
    const now = new Date();
    if (now.getTime() >= end_at_time) {
        clearInterval(intervalId);
        timePassed = true;
        console.log('Воркер завершив роботу');
        return;
    }

    sum += step;
    console.log('Поточна сума:', sum);
    const req =
        {
            balance:sum,
            id:id
        }
    const response = axios.post("http://localhost:8080/api/history/update/savings", req)
    console.log('Поточний час:', now.toLocaleString());
}, 60000);
