export const SignupYear = () => {
    let result = []
    let currentYear = new Date().getFullYear()
    for (let i = currentYear; i >=1950; i-- ) {
        result.push(i);
    };
    return result;
}