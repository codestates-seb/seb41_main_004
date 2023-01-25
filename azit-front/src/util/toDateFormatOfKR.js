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

export function numToDayOfTheWeek(num) {
  if(num === 0) {
    return "일"
  } else if (num === 1){
    return "월"
  } else if (num === 2){
    return "화"
  } else if (num === 3){
    return "수"
  } else if (num === 4){
    return "목"
  } else if (num === 5){
    return "금"
  } else if (num === 6){
    return "토"
  }
}