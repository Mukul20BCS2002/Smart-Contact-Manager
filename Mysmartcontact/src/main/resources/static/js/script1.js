console.log("This is js Page");

const toggleSidebar = () => {
    const sidebar = document.querySelector('.sidebar');
    const content = document.querySelector('.content');
    
    if (sidebar.style.display === "block") {
        // then close
        sidebar.style.display = "none";
        content.style.marginLeft = "0%";
    } else {
        // then open
        sidebar.style.display = "block";
        content.style.marginLeft = "20%";
    }
};


