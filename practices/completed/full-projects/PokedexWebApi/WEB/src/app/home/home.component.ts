import { Component } from '@angular/core';
import { AccountService } from '../_services';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent {
  constructor(private accountService: AccountService) {}
  logout() {
    this.accountService.logout();
  }
}
