using System.Collections;
using UnityEngine.UI;
using UnityEngine;
using Login;
using UnityEngine.Networking;
using static Login.LoginManager;

public class SaveTestResult : MonoBehaviour
{

    public LoginManager loginManager;
    public TestResult testResult;

    public TestResult testTestResult = new TestResult(new TestResult.Twohand(true, 0, 200), new TestResult.Conveyor(true, 1234), new TestResult.DigitSpan(true), new TestResult.Maze(false, 6), new TestResult.DecisionMaking(true, 0.123, 2, 3));

    public GameObject Savebutton;
    private bool isresultsended;


    // Start is called before the first frame update
    void Start()
    {
        Savebutton = GameObject.FindGameObjectWithTag("SaveButton");
        Savebutton.SetActive(false);
        isresultsended = false;
    }

    // Update is called once per frame
    void Update()
    {
        SaveButtonaAcivateSwitch();
    }

    private void SaveButtonaAcivateSwitch()
    {
        if ( Maze_move.maze_result && Move_key.two_hand_result && Conveyor_button.conveyor_result && DecisionMakingTest.isDMTEnded && DigitSpanGame.DigitGameresult)
        {
            Savebutton.SetActive(true);
        }
        if (isresultsended == true)
        {
            Savebutton.SetActive(false);

        }
    }

    /// <summary>
    /// 게임종료. 전처리기를 이용해 에디터 아닐때 종료.
    /// </summary>
    public void GameExit()
    {
        #if UNITY_EDITOR
                UnityEditor.EditorApplication.isPlaying = false;
        #else
                Application.Quit();
        #endif
    }

    public void Save()
    {

        print("clicked");
        TestResult testResult = new TestResult(
            new TestResult.Twohand(Menu_UI.conveyor_web_result, Conveyor_button.err_count, Conveyor_button.save_time), 
            new TestResult.Conveyor(Menu_UI.two_hand_web_result, Mathf.Round(UI_control.display_time)),
            new TestResult.DigitSpan(Menu_UI.DST_web_result), 
            new TestResult.Maze(Menu_UI.maze_web_result, Maze_move.Trigger_Count), 
            new TestResult.DecisionMaking(Menu_UI.DMT_web_result, DecisionMakingTest.Avg_GoClickTime, DecisionMakingTest.Go_missed, DecisionMakingTest.NoGo_clicked));
        StartCoroutine(SendTestResultTest(testResult));
        isresultsended = true;
    }
    IEnumerator SendTestResultTest(TestResult testResult)
    {
        print("coroutine start");
        // 백엔드 서버 주소
        string host = "oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080";
        string uri = "/subject/test-result";
        string url = "https://" + host + uri;
        // post form 내용 구성
        WWWForm form = new WWWForm();
        string result = JsonUtility.ToJson(testResult);
        print(result);

        print(LoginManager.loginToken);

        if (LoginManager.loginToken!=null)
        {
            // form을 이용해서 해당 주소로 요청 보냄
            UnityWebRequest www = UnityWebRequest.Post(url, result);
            byte[] JsonToSend = new System.Text.UTF8Encoding().GetBytes(result);
            www.uploadHandler = new UploadHandlerRaw(JsonToSend);
            www.SetRequestHeader("Content-Type", "application/json");
            www.SetRequestHeader("Authorization", loginToken);

            yield return www.SendWebRequest();

            // 에러가 없으면 결과를 받음.
            if (www.error == null)
            {
                print("json:" + www.downloadHandler.text);
                string data = www.downloadHandler.text;

                // json형태의 결과를 받아 역직렬화함.
                JsonResult jsonResult = JsonUtility.FromJson<JsonResult>(data);
                print(jsonResult.msg);
                print(jsonResult.results.username);
                print(jsonResult.results.token);
                print(jsonResult.statusCode);
            }
            else
            {
                print(www.error);
            }
        }


    }
}
