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
  constructor(private email: EmailService, private snack: MatSnackBar) { }

  ngOnInit(): void {
  }

  do_sendMail() {

    if (this.data.to == "" || this.data.subject == "" || this.data.message == "") {
      this.snack.open("Fields can not be empty !!! ", "Ok")
      return;
    }
    console.log("submit called...")
    console.log("Data : ", this.data)

    //calling backend service via service folder
    this.email.sendEmail(this.data).subscribe(
      response => {
        console.log(response);
      },
      error => {
        console.log(error)

      }
    )
  }
}
