import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  images: any[] | undefined;

  responsiveOptions: any[] = [
    {
      breakpoint: '1024px',
      numVisible: 5
    },
    {
      breakpoint: '768px',
      numVisible: 3
    },
    {
      breakpoint: '560px',
      numVisible: 1
    }
  ];

  constructor() {

  }

  ngOnInit() {
    this.images = [{ img: 'https://primefaces.org/cdn/primeng/images/galleria/galleria7.jpg' }, { img: 'https://primefaces.org/cdn/primeng/images/galleria/galleria8.jpg' }, { img: 'https://primefaces.org/cdn/primeng/images/galleria/galleria6.jpg' }, { img: 'https://primefaces.org/cdn/primeng/images/galleria/galleria6.jpg' }]
  }
}
