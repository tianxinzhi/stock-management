import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAttrValue } from 'app/shared/model/attr-value.model';

@Component({
  selector: 'jhi-attr-value-detail',
  templateUrl: './attr-value-detail.component.html'
})
export class AttrValueDetailComponent implements OnInit {
  attrValue: IAttrValue | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attrValue }) => (this.attrValue = attrValue));
  }

  previousState(): void {
    window.history.back();
  }
}
