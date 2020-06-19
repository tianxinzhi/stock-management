import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAttr } from 'app/shared/model/attr.model';

@Component({
  selector: 'jhi-attr-detail',
  templateUrl: './attr-detail.component.html'
})
export class AttrDetailComponent implements OnInit {
  attr: IAttr | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attr }) => (this.attr = attr));
  }

  previousState(): void {
    window.history.back();
  }
}
