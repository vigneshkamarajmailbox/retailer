import * as moment from 'moment';

export function dateTimeFormatter(date: Date) {
  return moment(date).format('YYYY-MM-DD hh:mm A');
}

//.add(8, 'hours')

export function getLoginExpireTime() {
  return moment(new Date().getTime()).add(8, 'hours').format('YYYY-MM-DD hh:mm A');
}

export function randomNumber(): number {
  return Math.floor(1000 + Math.random() * 9000);
}
export function randomString(length: number): string {
  let result = '';
  const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  const charactersLength = characters.length;
  let counter = 0;
  while (counter < length) {
    result += characters.charAt(Math.floor(Math.random() * charactersLength));
    counter += 1;
  }
  return result;
}
