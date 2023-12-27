import { Component } from '@angular/core';
import { AppMainComponent } from 'src/app/pages/main/app.main.component';

@Component({
  selector: 'app-topbar',
  templateUrl: './app.topbar.component.html'
})
export class AppTopBarComponent {

    constructor(public app: AppMainComponent) {}
}
``