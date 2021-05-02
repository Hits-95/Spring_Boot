const toggleSideBar = () => {
    console.log("Hitesh Ahire");
    if ($(".sidebar").is(":visible")) {
        //hide logic
        $(".sidebar").css("display", "none")
        $(".content").css("margin-left", "0%")
    } else {
        //show logic
        $(".sidebar").css("display", "block")
        $(".content").css("margin-left", "20%")
    }
}