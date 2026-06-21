  function previewImage(event) {
    const input = event.target;
    const preview = document.getElementById("preview");

    if (input.files && input.files[0]) {
      const reader = new FileReader();
      reader.onload = function (e) {
        preview.src = e.target.result;
      };
      reader.readAsDataURL(input.files[0]);
    }
  }


<!--Auto Monthly Rent-->
  document.getElementById("unitSelect").addEventListener("change", function () {

    let selectedOption = this.options[this.selectedIndex];

    let rent = selectedOption.getAttribute("data-rent");

    document.getElementById("monthlyRental").value = rent || "";
});