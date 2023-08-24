using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;


/// <summary>
/// 게임 결과 변수
/// Go 놓친 횟수 : Go_missed, int 자료형
/// NoGo 클릭 횟수 : NoGo_clicked ,int자료형
/// Go 누른 평균 시간 : AvgResult, string 자료형
/// isDMTEnded 변수 : 게임 통과 여부, bool 자료형
/// </summary>
/// 
///바꿔야 할 것 : 결과 화면 제거, 메뉴 화면에 결과 넘기기


public class DecisionMakingTest : MonoBehaviour
{

    public static string AvgResult;
    public static bool isDMTEnded;

    private GameObject GamestartPannel;
    private Button Gamestartbutton;

    string GoList, GoNoGoQuestion;//문제 담는 변수

    private GameObject QuestionDisplayGameobject;//사용자가 입력한 숫자를 유니티 화면에 표시해주는 역할
    private TextMesh QuestionDisplay; // 사용자가 입력한 숫자를 유니티 화면에 표시해줄 텍스트를 저장할 TextMesh 컴포넌트   

    public static int Go_clicked, Go_missed, NoGo_clicked; //버튼 클릭 판별
    public static float Sum_GoClickTime, Avg_GoClickTime;//계산 결과 담을 변수

    private float startTime;      // 시간 측정 시작 시간
    private float buttonTime;     // 버튼이 눌린 시간
    private float elapsedTime;    // 경과 시간
    public static bool buttonPressed;   // 버튼이 눌렸는지 여부

    private string[] GoNoGoQuestionList;

    // Start is called before the first frame update
    void Start()
    {


        isDMTEnded = false;

        GamestartPannel = GameObject.FindGameObjectWithTag("DMT_GamestartPanel");
        Gamestartbutton = GameObject.FindGameObjectWithTag("DMT_GamestartButton").GetComponent<Button>();
        QuestionDisplay = GameObject.FindGameObjectWithTag("DMT_QuestionText").GetComponent<TextMesh>();
        QuestionDisplay.text = "+";


        //GoList = new List<char>() { 'ㄱ', 'ㄴ', 'ㄷ', 'ㄹ', 'ㅁ' };
        //NoGoList = new List<char>() { 'ㅏ', 'ㅓ', 'ㅗ', 'ㅜ', 'ㅣ', '1', '2', '3', '4', '5' };

        //12가지 문자로 20개짜리 문자열 만들기. 자음 4개, 10개, 16개 비율로 해서 20자리 만들어두고 파이썬 돌려서 랜덤 문자열 만들기. 문자열 만들고 엑셀에서 좌우에 따옴표 넣고 합치면 문자 배열 변수에 바로 초기화 가능한 형태가 됨
        //이후 문자열 랜덤하게 선택해서 문제 출제할 수 있게 만들면 끝

        GoList = "ㄹㄴㅍㅈ";
        //ㄹㄴㅍㅈㅏㅔㅣㅜ3456 총 12가지
        GoNoGoQuestion = "ㄱㅏ2ㄴㅗㄹ1ㅜㄷ4";

        GoNoGoQuestionList = new string[30];
        

        Go_clicked = 0;
        Go_missed = 0;
        NoGo_clicked = 0;

        Sum_GoClickTime = 0;
        Avg_GoClickTime = 0;

        Gamestartbutton.onClick.AddListener(GameStartButtonClick);//게임시작


    }

    
    // Update is called once per frame
    void Update()
    {
        ButtonClick();
    }

    private void GameStartButtonClick()
    {
        Gamestartbutton.gameObject.SetActive(false);
        GamestartPannel.SetActive(false);
        StartCoroutine(GoNoGoGame());
    }


    public void ButtonClick()//버튼 클릭할 수 있게 하는 함수
    {
        if (Input.GetMouseButtonDown(0)) // 마우스 클릭 시
        {
            Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);
            RaycastHit hit;

            if (Physics.Raycast(ray, out hit))
            {
                if (hit.collider.CompareTag("DMT_InputButton")) //이 이름의 태그를 가진 버튼 오브젝트를 눌렀을 때
                {
                    buttonPressed = true;
                    buttonTime = Time.time;

                }
            }
        }
    }



    IEnumerator GoNoGoGame()
    {

        QuestionDisplay.text = '+'.ToString();
        yield return new WaitForSeconds(1.5f); // 1.5초 동안 처음 준비 화면 보여줌

        foreach (char q in GoNoGoQuestion)
        {
            buttonPressed = false;
            ///Go 에서는 누른 시간, 놓친 여부 측정
            if (GoList.Contains(q.ToString()))//눌러야 할 때
            {

                QuestionDisplay.text = q.ToString();
                yield return new WaitForSeconds(0.3f); // 0.3초 동안 문제 보여줌
                QuestionDisplay.text = "";//문제 사라짐
                startTime = Time.time;
                yield return new WaitForSeconds(1.8f); // 1.8초 동안 입력 대기

                if (buttonPressed == true)
                {
                    Go_clicked++;
                    elapsedTime = buttonTime - startTime;
                    Sum_GoClickTime += elapsedTime;
                }
                else
                {
                    //Go인데 못누른 카운트 추가
                    Go_missed++;
                }

                yield return new WaitForSeconds(0.5f); // 0.5초 동안 휴식
            }
            else//누르지 말아야 할 때
            {
                QuestionDisplay.text = q.ToString();
                yield return new WaitForSeconds(0.3f); // 0.3초 동안 문제 보여줌
                QuestionDisplay.text = "";//문제 사라짐
                yield return new WaitForSeconds(1.8f); // 1.8초 동안 입력 대기

                if (buttonPressed == true)
                {
                    NoGo_clicked++;
                }

                yield return new WaitForSeconds(0.5f); // 0.5초 동안 대기
            }
        }
        QuestionDisplay.text = "";//대기 화면 보여줌
        Counting();
        yield break;
    }
    

    /// <summary>
    /// 버튼이 눌리면 Go, NoGo 2가지로 나눠서 해야 함. 
    /// Go 일때는 누르면 시간 체크, 못누르면 카운트+1
    /// NoGo 일때는 누르면 카운트+1
    /// </summary>
    private void Counting()
    {
        

        Avg_GoClickTime = Sum_GoClickTime / Go_clicked;
        AvgResult = Avg_GoClickTime.ToString("F3");
        isDMTEnded = true;

        SceneManager.LoadScene("Main_menu", LoadSceneMode.Single);
    }




}


