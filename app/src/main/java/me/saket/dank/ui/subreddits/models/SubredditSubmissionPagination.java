package me.saket.dank.ui.subreddits.models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.auto.value.AutoValue;

import java.util.List;
import javax.inject.Inject;

import me.saket.dank.R;
import me.saket.dank.ui.subreddits.SubredditSubmissionsAdapter;
import me.saket.dank.utils.Optional;

public interface SubredditSubmissionPagination {

  @AutoValue
  abstract class UiModel implements SubredditScreenUiModel.SubmissionRowUiModel {
    @Override
    public long adapterId() {
      return SubredditSubmissionsAdapter.ADAPTER_ID_PAGINATION_FOOTER;
    }

    @Override
    public Type type() {
      return Type.PAGINATION_FOOTER;
    }

    public abstract boolean progressVisible();

    public abstract Optional<Integer> errorTextRes();

    public static UiModel create(boolean progressVisible, Optional<Integer> errorTextRes) {
      return new AutoValue_SubredditSubmissionPagination_UiModel(progressVisible, errorTextRes);
    }

    public static UiModel createProgress() {
      return create(true, Optional.empty());
    }

    public static UiModel createError(Integer errorTextRes) {
      return create(false, Optional.of(errorTextRes));
    }
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    private final View progressView;
    private final TextView errorTextView;

    public ViewHolder(View itemView) {
      super(itemView);
      progressView = itemView.findViewById(R.id.infinitescroll_footer_progress);
      errorTextView = itemView.findViewById(R.id.infinitescroll_footer_error);
    }

    public static ViewHolder create(LayoutInflater inflater, ViewGroup parent) {
      return new ViewHolder(inflater.inflate(R.layout.list_item_subreddit_pagination_footer, parent, false));
    }

    public void bind(UiModel uiModel) {
      progressView.setVisibility(uiModel.progressVisible() ? View.VISIBLE : View.GONE);
      errorTextView.setVisibility(uiModel.errorTextRes().isPresent() ? View.VISIBLE : View.GONE);
      if (uiModel.errorTextRes().isPresent()) {
        errorTextView.setText(uiModel.errorTextRes().get());
      }
    }
  }

  class Adapter implements SubredditScreenUiModel.SubmissionRowUiChildAdapter<UiModel, ViewHolder> {
    @Inject
    public Adapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent) {
      return ViewHolder.create(inflater, parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, UiModel uiModel) {
      holder.bind(uiModel);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, UiModel uiModel, List<Object> payloads) {
      throw new UnsupportedOperationException();
    }
  }
}
