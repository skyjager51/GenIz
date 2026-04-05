function wrapdefaultActions(setErrorMessage, setErrorText, err){
    setErrorMessage(true);
    setErrorText(err.response?.data?.exceptionErrorMessage);

    setTimeout(() => {
        setErrorMessage(false);
    }, 5000);
}

export default wrapdefaultActions;
                