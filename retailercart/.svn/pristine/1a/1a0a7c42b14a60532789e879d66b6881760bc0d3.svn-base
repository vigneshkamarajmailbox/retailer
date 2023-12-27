import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-updatetoaster',
  templateUrl: './updatetoaster.component.html',
  styleUrls: ['./updatetoaster.component.css']
})
export class UpdatetoasterComponent implements OnInit {

  @Input() show = false;

  constructor() {
  }

  ngOnInit(): void {
  }

  reload() {
    setTimeout(() => {
      window.location.reload();
    }, 1000);
  }
}
