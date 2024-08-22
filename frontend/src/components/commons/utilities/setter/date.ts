export const changeYYMMDD = (setDate: string) => {
    const change_date = new Date(setDate);
    const year = change_date.getFullYear();
    const month = change_date.getMonth() + 1;
    const date = change_date.getDate();

    const new_month = month < 10 ? "0" + month : month;
    const new_date = date < 10 ? "0"+date : date;
    
    return year + "-" + new_month + "-" + new_date;
}