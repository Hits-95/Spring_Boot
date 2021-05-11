import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EmailService {

  private base_url = "http://127.0.0.1:8080";

  constructor(private http: HttpClient) { }

  sendEmail(data: any) {
    return this.http.post(this.base_url + "/send-email", data);
  }
}
