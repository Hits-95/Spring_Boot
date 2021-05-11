import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EmailService } from 'src/app/service/email.service';


@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
})
export class EmailComponent implements OnInit {
  data = {
    to: "",
    subject: "",
    message: ""
  }

  //process bar denotion
  flag: boolean = false;
  constructor(private email: EmailService, private snack: MatSnackBar) { }

  ngOnInit(): void {
  }

  do_sendMail() {

    //vallidation for empty fields...
    if (this.data.to == "" || this.data.subject == "" || this.data.message == "") {
      this.snack.open("Fields can not be empty !!! ", "Ok")
      return;
    }

    console.log("submit called...")
    console.log("Data : ", this.data)

    //calling backend service via service folder
    this.flag = true;
    this.email.sendEmail(this.data).subscribe(
      response => {
        console.log(response)
        this.flag = false
        this.snack.open("Mail has been sended successfully !!!", "Ok")
      },
      error => {
        console.log(error)
        this.flag = false
        this.snack.open("Mail has not been sended !!! try again...", "Ok")
      }
    )
  }
}
