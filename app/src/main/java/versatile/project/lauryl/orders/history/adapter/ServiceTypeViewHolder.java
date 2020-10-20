package versatile.project.lauryl.orders.history.adapter;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.TextView;


import androidx.appcompat.widget.AppCompatImageView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import versatile.project.lauryl.R;
import versatile.project.lauryl.orders.history.model.ServiceType;

public class ServiceTypeViewHolder extends ParentViewHolder {
  private static final float INITIAL_POSITION = 0.0f;
  private static final float ROTATED_POSITION = 180f;
  private TextView genreTitle;
  private AppCompatImageView imageView;

  public ServiceTypeViewHolder(View itemView) {
    super(itemView);
    genreTitle = itemView.findViewById(R.id.txtServiceName);
    imageView=itemView.findViewById(R.id.imgArrow);

  }
  public void bind(ServiceType recipe) {
    genreTitle.setText(recipe.getTile());
  }
  @Override
  public void setExpanded(boolean expanded) {
    super.setExpanded(expanded);

    if (expanded) {
      imageView.setRotation(ROTATED_POSITION);
    } else {
      imageView.setRotation(INITIAL_POSITION);
    }

  }
  @Override
  public void onExpansionToggled(boolean expanded) {
    super.onExpansionToggled(expanded);
    RotateAnimation rotateAnimation;
    if (expanded) { // rotate clockwise
      rotateAnimation = new RotateAnimation(ROTATED_POSITION,
              INITIAL_POSITION,
              RotateAnimation.RELATIVE_TO_SELF, 0.5f,
              RotateAnimation.RELATIVE_TO_SELF, 0.5f);
    } else { // rotate counterclockwise
      rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
              INITIAL_POSITION,
              RotateAnimation.RELATIVE_TO_SELF, 0.5f,
              RotateAnimation.RELATIVE_TO_SELF, 0.5f);
    }
    rotateAnimation.setDuration(200);
    rotateAnimation.setFillAfter(true);
    imageView.startAnimation(rotateAnimation);
  }
}