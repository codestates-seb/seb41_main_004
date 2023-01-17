export function toDateFormatOfKR(date) {
  return date.toLocaleString("ko-KR");
}

export function toDateFormatOfMonthDay(date, time) {
  let month = date.slice(5, 7);
  let day = date.slice(8, 10);
  let timeSlice = time.slice(0, 5);
  let result = `${month}월${day}일 ${timeSlice}`;
  return result
}