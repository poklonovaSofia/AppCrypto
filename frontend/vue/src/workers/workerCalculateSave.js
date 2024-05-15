import axios from "axios";
let sum = 0;
let step = 0;
let timePassed = false;
let endTime = new Date();
let id=0;

self.onmessage = (event) => {
    const { initialValue, hours, savings_id } = event.data;
    step = initialValue;
    id=savings_id;
    const now = new Date();
    endTime = now.getTime() + (hours * 60 * 60 * 1000);
};

const intervalId = setInterval(() => {
    const now = new Date();

    if (now.getTime() >= endTime) {
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
