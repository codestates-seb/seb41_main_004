export function toDateFormatOfKR(date) {
  return date.toLocaleString("ko-KR");
}

export function toDateFormatOfMonthDay(data) {
  let month = data.slice(5, 7);
  let day = data.slice(8, 10);
  let time = data.slice(-5);
  let date = `${month}월${day}일 ${time}`;
  return date
}